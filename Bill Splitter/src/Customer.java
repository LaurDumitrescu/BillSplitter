import java.util.Map;
import java.util.TreeMap;

public class Customer implements Comparable<Customer>{

    private Map<Product, Integer> products = new TreeMap<>();
    private String name;

    public Customer(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void add(Product p, int quantity){
        if(products.containsKey(p)){
            products.replace(p, products.get(p) + quantity);
        }
        else{
            products.put(p, quantity);
        }
    }


    @Override
    public int compareTo(Customer o) {
        if(name.equals(o.name)) return 0;
        return name.compareTo(o.name);
    }
}
