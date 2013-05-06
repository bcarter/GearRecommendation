package gov.osha.GearRecommendation;

public class Criteria {
    private int id;
    private String criteria;
    private String type;
    private String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return criteria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Criteria criteria1 = (Criteria) o;

        if (id != criteria1.id) return false;
        if (criteria != null ? !criteria.equals(criteria1.criteria) : criteria1.criteria != null) return false;
        if (!information.equals(criteria1.information)) return false;
        if (type != null ? !type.equals(criteria1.type) : criteria1.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (criteria != null ? criteria.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + information.hashCode();
        return result;
    }
}
