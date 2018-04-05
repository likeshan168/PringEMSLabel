package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 注意两个注释：
 *
 * @XmlRootElement 定义根元素的名称。
 * @XmlElement 一个可选的名称，用来指定元素。
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {
    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
