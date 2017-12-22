package app.services.response;

import org.springframework.http.HttpStatus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Bublik on 18-Dec-17.
 * todo: test
 */
public class ExceptionResponseMapping implements Cloneable{

    private LinkedList<ExceptionStatusPair> pairs = new LinkedList<>();
    private HttpStatus defaultStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ExceptionResponseMapping() {
    }

    public ExceptionResponseMapping(ExceptionResponseMapping source) {
        this(source.pairs);
    }

    private ExceptionResponseMapping(List<ExceptionStatusPair> pairs){
        //this.pairs.addAll(pairs.stream().map(ExceptionStatusPair::clone).collect(Collectors.toList()));
        for (ExceptionStatusPair pair: pairs){
            this.pairs.add(pair.clone());
        }
        // TODO: 22-Dec-17 compare speed
    }

    public void setAll(ExceptionResponseMapping mapping){
        mapping.pairs.forEach(this::set);
    }

    private void set(ExceptionStatusPair pair){
        set(pair.e, pair.s);
    }

    public void set(Exception e, HttpStatus status){
        ListIterator<ExceptionStatusPair> iterator = pairs.listIterator();
        while (iterator.hasNext()){
            ExceptionStatusPair current = iterator.next();
            if (equal(current.e, e)){
                current.s = status;
                return;
            } else if (instance(e, current.e)) {
                iterator.previous();
                iterator.add(new ExceptionStatusPair(e, status));
                return;
            }
        }
        pairs.add(new ExceptionStatusPair(e, status));
    }

    public HttpStatus get(Exception e){
        for (ExceptionStatusPair pair: pairs){
            if (instance(e, pair.e)){
                return pair.s;
            }
        }
        return defaultStatus;
    }

    public void remove(Exception e){
        remove(e, false, false);
    }

    public void removeInstance(Exception e){
        remove(e, true, false);
    }

    public void removeAllInstances(Exception e){
        remove(e, true, true);
    }

    private void remove(Exception e, boolean instance, boolean all){
        Iterator<ExceptionStatusPair> iterator = pairs.iterator();
        while (iterator.hasNext()){
            ExceptionStatusPair pair = iterator.next();
            if (!instance && equal(e, pair.e)){
                iterator.remove();
                return;
            } else if (instance && instance(e, pair.e)){
                iterator.remove();
                if (!all) return;
            }
        }
    }

    private boolean instance(Exception request, Exception registered){
        return registered.getClass().isInstance(request);
    }

    private boolean equal(Exception e1, Exception e2){
        return e1.getClass().equals(e2.getClass());
    }

    public void clear(){
        pairs.clear();
    }


    private class ExceptionStatusPair implements Cloneable{
        Exception e;
        HttpStatus s;

        public ExceptionStatusPair() {
        }

        ExceptionStatusPair(Exception e, HttpStatus s) {
            this.e = e;
            this.s = s;
        }

        @Override
        protected ExceptionStatusPair clone() {
            return new ExceptionStatusPair(e,s);
        }

        @Override
        public String toString() {
            return e.getClass().getSimpleName()+":"+s.name()+"("+s.toString()+")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this==obj) return true;
            if (!(obj instanceof ExceptionStatusPair)) return false;
            ExceptionStatusPair esp = (ExceptionStatusPair) obj;
            return (e.getClass().equals(esp.e.getClass()) && s.equals(esp.s));
        }
    }

    public HttpStatus getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(HttpStatus defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    @Override
    public ExceptionResponseMapping clone() {
        ExceptionResponseMapping erm = new ExceptionResponseMapping(this);
        return erm;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        boolean first = true;
        for (ExceptionStatusPair pair: pairs){
            if (first) first = false; else b.append(", ");
            b.append(pair.toString());
        }
        return b.append(']').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ExceptionResponseMapping)) return false;
        ExceptionResponseMapping erm = (ExceptionResponseMapping) obj;
        return erm.pairs.size() == this.pairs.size() &&
                this.defaultStatus.equals(erm.defaultStatus) &&
                this.pairs.equals(erm.pairs);
    }
}
