package repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Model;
import io.ebean.Transaction;
import models.ProductMy;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private EbeanServer ebeanServer;

    @Inject
    public ProductRepository(EbeanConfig ebeanConfig) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
    }

    public List<ProductMy> getAllProducts() {
        return ebeanServer.find(ProductMy.class).findList();
    }

    public Optional<ProductMy> lookup(Long id) {
        return Optional.ofNullable(ebeanServer.find(ProductMy.class).setId(id).findOne());
    }

    public Optional<Long> update(Long id, ProductMy product) {
        Transaction txn = ebeanServer.beginTransaction();
        Optional<Long> value = Optional.empty();
        try {
            ProductMy savedProduct = ebeanServer.find(ProductMy.class).setId(id).findOne();
            if (savedProduct != null) {
                savedProduct.releaseDate = product.releaseDate;
                savedProduct.model = product.model;
                savedProduct.brand = product.brand;
                savedProduct.article = product.article;
                savedProduct.update();
                txn.commit();
                value = Optional.of(id);
            }
        } finally {
            txn.end();
        }
        return value;
    }

    public Optional<Long> delete(Long id) {
        try {
            Optional<ProductMy> productOptional = Optional.ofNullable(ebeanServer.find(ProductMy.class).setId(id).findOne());
            productOptional.ifPresent(Model::delete);
            return productOptional.map(c -> c.id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Long> insert(ProductMy product) {
        if (product.releaseDate == null) product.releaseDate = new Date();
        ebeanServer.insert(product);
        return Optional.of(product.id);
    }
}
