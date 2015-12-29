package com.dev.tim.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ShopperDaoGenerator {

    private static final int VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(VERSION, "com.dev.tim.shopper_data.greendao");

        createItemDb(schema);
        java.io.File file = new java.io.File("../shopper-data/src/main/java");
        file.mkdirs();
        new DaoGenerator().generateAll(schema, "../shopper-data/src/main/java");
    }

    public static void createItemDb(Schema schema) {
        Entity itemDb = schema.addEntity("ItemDb");

        itemDb.addLongProperty("id").notNull().primaryKey();
        itemDb.addStringProperty("name");
        itemDb.addStringProperty("category");
        itemDb.addStringProperty("created_at");
        itemDb.addStringProperty("updated_at");
        itemDb.addStringProperty("user_id");
    }
}
