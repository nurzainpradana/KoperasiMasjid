package com.nurzainpradana.koperasimasjid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nurzainpradana.koperasimasjid.adapter.ProductsAdapter;
import com.nurzainpradana.koperasimasjid.fragment.BerandaFragment;
import com.nurzainpradana.koperasimasjid.fragment.FavoriteFragment;
import com.nurzainpradana.koperasimasjid.fragment.KeranjangFragment;
import com.nurzainpradana.koperasimasjid.fragment.ProfileFragment;
import com.nurzainpradana.koperasimasjid.fragment.TransaksiFragment;
import com.nurzainpradana.koperasimasjid.model.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //List store all product
    List<Product> productList;

    //RecyclerView
    RecyclerView recyclerView;
    ProductsAdapter adapter;

    //BottomNavBar
    private final String SELECTED_MENU = "selected_menu";
    BottomNavigationView navView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            if(item.getItemId() == R.id.navigation_beranda){
                fragment = BerandaFragment.newInstance();
            } else if(item.getItemId() == R.id.navigation_transaksi){
                fragment = TransaksiFragment.newInstance();
            } else if(item.getItemId() == R.id.navigation_keranjang){
                fragment = KeranjangFragment.newInstance();
            } else if(item.getItemId() == R.id.navigation_favorite){
                fragment = FavoriteFragment.newInstance();
            } else if(item.getItemId() == R.id.navigation_profil){
                fragment = ProfileFragment.newInstance();
            }

            if(fragment != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, fragment)
                        .commit();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState != null){
            savedInstanceState.getInt(SELECTED_MENU);
        } else {
            navView.setSelectedItemId(R.id.navigation_beranda);
        }

    }

    //Bottom Navigation Selected
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU, navView.getSelectedItemId());
    }
}