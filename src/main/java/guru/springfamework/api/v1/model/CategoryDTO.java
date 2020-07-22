package guru.springfamework.api.v1.model;

import lombok.*;

import java.io.Serializable;

/**
 * Created by jt on 9/24/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryDTO implements Serializable {
    final static long serialVersionUID = 2086084393710666005L;
    private Long id;
    private String name;
}
