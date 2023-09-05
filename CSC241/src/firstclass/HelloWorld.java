package firstclass;


public class HelloWorld {
    private String firstname;
    private String lastname;

    public HelloWorld(String fname, String lname){
        firstname = fname;
        lastname = lname;
    }


     String fullname(){
        return firstname+lastname;
    }






}
