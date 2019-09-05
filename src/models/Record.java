package models;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "records")
@NamedQueries({
    @NamedQuery(
            name = "getAllRecords",
            query = "SELECT r FROM Record AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getRecordsCount",
            query = "SELECT COUNT(r) FROM Record AS r"
            ),
    @NamedQuery(
            name = "getMyAllRecords",
            query = "SELECT r FROM Record AS r WhERE r.user = :user ORDER BY r.date DESC"
            ),
    @NamedQuery(
            name = "getMyRecordsCount",
            query = "SELECT COUNT(r) FROM Record AS r WHERE r.user = :user"
            ),
    @NamedQuery(
            name = "getSameDateRecord",
            query = "SELECT COUNT(r) FROM Record AS r WHERE r.date = :date"
            )
})
@Entity
public class Record {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private Date date;

    @Lob
    @Column(name = "breakfast", nullable = false)
    private String breakfast;

    @Lob
    @Column(name = "lunch", nullable = false)
    private String lunch;

    @Lob
    @Column(name = "dinner", nullable = false)
    private String dinner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }
}