public class FactoryPattern {
    public static void main(String[] args){
        Coffee coffee = CoffeeFactory.createCoffee(CoffeeType.LATTE);
        System.out.println(coffee);
    }
}

enum CoffeeType{
    LATTE,
    ESPRESSO
}

abstract class Coffee{
    protected String name;
    public String getName(){
        return name;
    }
}

class Latte extends Coffee{
    public Latte(){
        this.name = "Latte";
    }
}

class Espresso extends Coffee{
    public Espresso(){
        this.name = "Espresso";
    }
}

class CoffeeFactory{
    public static Coffee createCoffee(CoffeeType name){
        switch(name){
            case LATTE: return new Latte();
            case ESPRESSO: return new Espresso();
            default: throw new IllegalArgumentException("Unknown coffee type: " + name);
        }
    }
}