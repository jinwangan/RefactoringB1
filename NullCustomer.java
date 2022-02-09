public class NullCustomer extends ConcreteCustomer{
    public NullCustomer(String name) {
        super(name);
    }

    @Override
    public void printCustomerInfo(){
        System.out.println("No customer found") ;
    }

}
