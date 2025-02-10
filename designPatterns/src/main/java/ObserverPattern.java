import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    public static void main(String[] args){
        Topic topic = new Topic();
        Observer a = new TopicSubscriber("a",topic);
        Observer b = new TopicSubscriber("b",topic);
        Observer c = new TopicSubscriber("c",topic);
        topic.register(a);
        topic.register(b);
        topic.register(c);

        topic.postMessage("amumu is op champion");
    }
}

interface Subject {
    public void register(Observer obj);
    public void unregister(Observer obj);
    public void notifyObservers();
    public Object getUpdate(Observer obj);
}

interface Observer {
    public void update();
}

class Topic implements Subject{
    private List<Observer> observers;
    private String message;
    public Topic() {
        this.observers = new ArrayList<>();
        this.message = "";
    }

    @Override
    public void register(Observer obj) {
        if(!observers.contains(obj)) observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(Observer::update);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return this.message;
    }

    public void postMessage(String message) {
        System.out.println("message sended to Topic "+message);
        this.message = message;
        notifyObservers();
    }
}

class TopicSubscriber implements Observer {
    private String name;
    private Subject subject;

    public TopicSubscriber(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void update(){
        String msg = (String)subject.getUpdate(this);
        System.out.println(name + ":: got message >>"+ msg);
    }
}

