package controllers;

import models.ProductMy;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repository.ProductRepository;
import views.html.editForm;
import views.html.index;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class HomeController extends Controller {

    private ProductRepository productRepository;
    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory, ProductRepository computerRepository) {
        this.productRepository = computerRepository;
        this.formFactory = formFactory;
    }

    private Result GO_HOME = Results.redirect(routes.HomeController.list());

    public Result index() {
        return ok(index.render());
    }

    public Result list() {
        List<ProductMy> list = productRepository.getAllProducts();
        return ok(views.html.list.render(list));
    }

    public Result showEditForm(Long id) {
        Optional<ProductMy> c = productRepository.lookup(id);
        Form<ProductMy> productForm = formFactory.form(ProductMy.class).fill(c.get());
        return ok(editForm.render(id, productForm));
    }

    public Result update(Long id) throws PersistenceException {
        Form<ProductMy> productForm = formFactory.form(ProductMy.class).bindFromRequest();
        if (productForm.hasErrors()) {
            return badRequest(editForm.render(id, productForm));
        } else {
            ProductMy newProduct = productForm.get();
            productRepository.update(id, newProduct);
            flash("success", "Product " + newProduct.brand + " has been updated");
            return GO_HOME;
        }
    }

    public Result showCreateForm() {
        Form<ProductMy> productForm = formFactory.form(ProductMy.class);
        return ok(views.html.createForm.render(productForm));
    }

    public Result save() {
        Form<ProductMy> productForm = formFactory.form(ProductMy.class).bindFromRequest();
        if (productForm.hasErrors()) {
            return badRequest(views.html.createForm.render(productForm));
        }
        ProductMy product = productForm.get();
        productRepository.insert(product);
        flash("success", "Product " + product.brand + " has been created");
        return GO_HOME;
    }

    public Result delete(Long id) {
        productRepository.delete(id);
        flash("success", "Product has been deleted");
        return GO_HOME;
    }
}
            
