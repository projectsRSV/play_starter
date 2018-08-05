package repository;

import io.ebean.Model;
import models.ProductMy;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

//    private EbeanServer ebeanServer;

/*
    @Inject
    public ProductRepository(EbeanConfig ebeanConfig) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
    }
*/

    public List<ProductMy> getAllProducts() {
//        return ebeanServer.find(ProductMy.class).findList();
        return ProductMy.find.all();
    }

    public Optional<ProductMy> lookup(Long id) {
//        return Optional.ofNullable(ebeanServer.find(ProductMy.class).setId(id).findOne());
        return Optional.ofNullable(ProductMy.find.byId(id));
    }

    public Optional<Long> update(Long id, ProductMy product) {
//        Transaction txn = ebeanServer.beginTransaction();
        Optional<Long> value = Optional.empty();
        //            ProductMy savedProduct = ebeanServer.find(ProductMy.class).setId(id).findOne();
        ProductMy savedProduct = ProductMy.find.byId(id);
        if (savedProduct != null) {
            savedProduct.releaseDate = product.releaseDate;
            savedProduct.model = product.model;
            savedProduct.brand = product.brand;
            savedProduct.article = product.article;
            savedProduct.update();
            savedProduct.save();
            value = Optional.of(id);
        }
        return value;
    }

    public Optional<Long> delete(Long id) {
        try {
//            Optional<ProductMy> productOptional = Optional.ofNullable(ebeanServer.find(ProductMy.class).setId(id).findOne());
            Optional<ProductMy> productOptional = Optional.ofNullable(ProductMy.find.byId(id));
            productOptional.ifPresent(Model::delete);
            return productOptional.map(c -> c.id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Long> insert(ProductMy product) {
        if (product.releaseDate == null) product.releaseDate = new Date();
//        ebeanServer.insert(product);
        product.save();
        return Optional.of(product.id);
    }
}
