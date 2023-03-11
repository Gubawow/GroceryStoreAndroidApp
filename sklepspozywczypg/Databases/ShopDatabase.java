package com.example.sklepspozywczypg.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sklepspozywczypg.GroceryItem;

import java.util.ArrayList;

@Database(entities = {GroceryItem.class, CartItem.class}, version=1)
public abstract class ShopDatabase extends RoomDatabase {

    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();

    //Singleton pattern ;)
    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {
        if(null == instance){
            instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialAsyncTask(instance).execute();

        }
    };

    private static class InitialAsyncTask extends AsyncTask<Void, Void, Void>{


        private GroceryItemDao groceryItemDao;


        public InitialAsyncTask(ShopDatabase db) {
            this.groceryItemDao = db.groceryItemDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<GroceryItem> allItems = new ArrayList<>();
            GroceryItem milk = new GroceryItem("Mleko", "Mleko to smak radości dla mocnych kości",
                    "https://www.carrefour.pl/images/product/org/laciate-mleko-uht-32-1-l-30mske.jpg", "Napoje",
                    4.5, 12);
            allItems.add(milk);

            GroceryItem iceCream = new GroceryItem("Lody Ekipy", "Lody lody dla ochlody",
                    "https://d-art.ppstatic.pl/kadry/k/r/1/61/47/60813ae05dd62_o_full.jpg",
                    "Jedzenie", 2, 44);
            allItems.add(iceCream);

            GroceryItem soda = new GroceryItem("Coca-cola w puszce", "Podatek na cukier sprawil ze cena napojów " +
                    "gazowanych wyskoczyla w kosmos",
                    "https://t3.ftcdn.net/jpg/02/82/94/38/360_F_282943841_SlQtzi4IaLjpXjV0bvsPSA6Nc3f5NHFf.jpg",
                    "Napoje", 3.2, 80);
            allItems.add(soda);

            GroceryItem shampoo = new GroceryItem("Szampon", "Służy do mycia włosów głowy",
                    "https://pollena.com.pl/6025-thickbox_default/bialy-jelen-szampon-do-wlosow-owoc-i-ziolo-lopianjagoda-400-ml.jpg",
                    "Kosmetyki", 24.99, 17);
            allItems.add(shampoo);

            GroceryItem spaghetti = new GroceryItem("Spaghetti",
                    "Makaron spaghetti świetnie nadaje się do gotowania potraw kuchni włoskiej.",
                    "https://www.idelikatesy24.pl/userdata/public/gfx/3265/spaghetti.jpg",
                    "Jedzenie", 5.49, 32);
            allItems.add(spaghetti);

            GroceryItem soap = new GroceryItem("Mydło", "Kostka mydła do zmycia brudów zeszłej nocy (albo przyszłej)",
                    "https://www.fhgerman.pl/userdata/public/gfx/51475/1133_3006.jpg",
                    "Kosmetyki", 3.99, 28);
            allItems.add(soap);

            GroceryItem juice = new GroceryItem("Sok pomarańczowy", "Sok pomarańczowy, 100% owoców, 300% smaku!",
                    "https://www.carrefour.pl/images/product/org/cappy-sok-pomaranczowy-100-1-l-p67hkc.jpg",
                    "Napoje", 4.49, 25);
            allItems.add(juice);

            GroceryItem walnut = new GroceryItem("Piwo Lech w puszce", "Jeśli masz 18 lat i piwo to twoje paliwo to świetnie trafiłeś ;)",
                    "https://delikatesy-koszyk.pl/environment/cache/images/500_500_productGfx_3301/piwo-lech-premium-05l-puszka.jpg",
                    "Alkohol", 3.99, 64);
            allItems.add(walnut);

            GroceryItem pistachio = new GroceryItem("Bourbon Jim Beam 0.5L", "Sami nie wiemy jaka jest różnica między tanim whiskey, a tanim bourbonem.",
                    "https://www.aporvino.com/3668-thickbox_default/jim-beam.jpg",
                    "Alkohol", 49.99, 15);
            allItems.add(pistachio);

            for(GroceryItem g: allItems){
                groceryItemDao.insert(g);
            }

            return null;
        }
    }
}
