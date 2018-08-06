package models;

import io.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class ProductMy extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    @Size(min = 3, max = 255, message = "length min = 3 max 255")
    public String brand;

    @Constraints.Required
    public String model;

    @Constraints.Required
    public String article;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date releaseDate;

}

