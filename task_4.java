import java.util.*;
import java.util.concurrent.*;

public class task_4 {

    static Scanner sc = new Scanner(System.in);
    static HashMap<String, User> data = new HashMap<>();
    static ArrayList<Question> qlist = new ArrayList<>();
    static User curUser = null;
    static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        loadData();
        home();
        service.shutdownNow();
    }

    static void loadData(){
        // just adding some sample users
        data.put("Abhi", new User("Abhi","1234","Abhishek Nishad","abhinishad123@gmail.com"));
        data.put("Abhijit", new User("Abhijit","abcd","Abhijit Neogi","abhijit12@gmail.com"));

        // questions for demo
        qlist.add(new Question("What is 2 + 2 ?", Arrays.asList("3","4","5","6"),1));
        qlist.add(new Question("Java is a ?", Arrays.asList("Fruit","Programming Lang","OS","Device"),1));
        qlist.add(new Question("JVM stands for ?", Arrays.asList("Java Virtual Machine","Java Very Much","Just Video Mode","None"),0));
    }

    static void home(){
        while(true){
            System.out.println("\n=== Simple Exam System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("enter choice : ");
            String ch = sc.nextLine();
            if(ch.equals("1")) login();
            else if(ch.equals("2")){
                System.out.println("Thank You");
                break;
            } else System.out.println("wrong input");
        }
    }

    static void login(){
        System.out.print("username : ");
        String u = sc.nextLine();
        System.out.print("password : ");
        String p = sc.nextLine();

        User usr = data.get(u);
        if(usr!=null && usr.pass.equals(p)){
            curUser = usr;
            System.out.println("welcome "+usr.name);
            afterLogin();
        }else{
            System.out.println("invalid login");
        }
    }

    static void afterLogin(){
        while(curUser!=null){
            System.out.println("\n1. update profile");
            System.out.println("2. change password");
            System.out.println("3. start test");
            System.out.println("4. logout");
            System.out.print("choose : ");
            String c = sc.nextLine();

            switch(c){
                case "1": updateProfile(); break;
                case "2": changePassword(); break;
                case "3": startTest(qlist, 45); break;
                case "4": logout(); break;
                default: System.out.println("invalid");
            }
        }
    }

    static void updateProfile(){
        System.out.print("new name (blank skip): ");
        String n = sc.nextLine();
        if(!n.isBlank()) curUser.name = n;

        System.out.print("new email (blank skip): ");
        String e = sc.nextLine();
        if(!e.isBlank()) curUser.email = e;

        System.out.println("profile updated");
    }

    static void changePassword(){
        System.out.print("old pass : ");
        String op = sc.nextLine();
        if(!curUser.pass.equals(op)){
            System.out.println("wrong pass");
            return;
        }
        System.out.print("new pass : ");
        String np = sc.nextLine();
        curUser.pass = np;
        System.out.println("changed");
    }

    static void startTest(List<Question> ql, int seconds){
        System.out.println("test started... time "+seconds+" sec");
        TestSession t = new TestSession(ql);
        ScheduledFuture<?> auto = service.schedule(() -> {
            System.out.println("\nTime up! Auto submit...");
            t.auto = true;
            t.submit();
            t.showResult();
        }, seconds, TimeUnit.SECONDS);

        for(int i=0;i<ql.size();i++){
            if(t.auto) break;
            Question q = ql.get(i);
            System.out.println("\nQ"+(i+1)+": "+q.text);
            for(int j=0;j<q.options.size();j++){
                System.out.println((j+1)+") "+q.options.get(j));
            }
            System.out.print("ans (1-4) or 'submit' or 'exit' : ");
            String ans = sc.nextLine();

            if(ans.equalsIgnoreCase("submit")){
                t.submit();
                auto.cancel(false);
                t.showResult();
                break;
            } else if(ans.equalsIgnoreCase("exit")){
                System.out.println("test closed");
                t.close();
                auto.cancel(false);
                logout();
                return;
            } else {
                try{
                    int a = Integer.parseInt(ans)-1;
                    t.save(i,a);
                }catch(Exception e){
                    System.out.println("wrong");
                    i--;
                }
            }
        }

        if(!t.done && !t.auto){
            System.out.println("auto submit at end");
            t.submit();
            auto.cancel(false);
            t.showResult();
        }
    }

    static void logout(){
        System.out.println("logged out");
        curUser = null;
    }

    // inner classes (simple)
    static class User{
        String uname, pass, name, email;
        User(String u,String p,String n,String e){
            uname=u;pass=p;name=n;email=e;
        }
    }

    static class Question{
        String text;
        List<String> options;
        int correct;
        Question(String t,List<String> o,int c){
            text=t;options=o;correct=c;
        }
    }

    static class TestSession{
        List<Question> qs;
        Map<Integer,Integer> answers = new HashMap<>();
        boolean done=false, auto=false;
        TestSession(List<Question> q){ qs=q; }

        void save(int n,int a){
            answers.put(n,a);
            System.out.println("saved");
        }

        void submit(){
            done=true;
            System.out.println("submitted");
        }

        void close(){
            System.out.println("session closed");
        }

        void showResult(){
            int s=0;
            for(int i=0;i<qs.size();i++){
                Integer a = answers.get(i);
                if(a!=null && a==qs.get(i).correct) s++;
            }
            System.out.println("Score: "+s+"/"+qs.size());
        }
    }
}

