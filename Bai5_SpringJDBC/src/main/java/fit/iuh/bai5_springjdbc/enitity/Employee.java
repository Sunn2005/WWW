package fit.iuh.bai5_springjdbc.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String role;
    private Double salary;
}
