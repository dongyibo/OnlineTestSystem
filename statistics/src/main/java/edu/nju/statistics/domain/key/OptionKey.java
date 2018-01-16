package edu.nju.statistics.domain.key;

import java.io.Serializable;

public class OptionKey implements Serializable {
    private int id;
    private String quesId;

    public OptionKey() {
    }

    public OptionKey(int id, String quesId) {
        this.id = id;
        this.quesId = quesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + id * PRIME;
        result = PRIME * result + ((quesId == null) ? 0 : quesId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        final OptionKey op = (OptionKey) obj;
        if (id != op.id){
            return false;
        }
        if (quesId == null){
            return op.quesId == null;
        }
        return quesId.equals(op.quesId);
    }
}
