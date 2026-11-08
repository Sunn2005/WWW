package fit.iuh.springdatajpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "positions")
public class Position {
    @Id
    private int id;
    private String title;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private List<Employee> employees;
}
