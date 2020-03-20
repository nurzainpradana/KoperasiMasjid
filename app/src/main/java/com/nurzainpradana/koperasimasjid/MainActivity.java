package com.nurzainpradana.koperasimasjid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    //BottomNavBar
    private final String SELECTED_MENU = "selected_menu";
    BottomNavigationView bottomNavigationView;

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

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState != null){
            savedInstanceState.getInt(SELECTED_MENU);
        } else {
            bottomNavigationView.setSelectedItemId(R.id.navigation_beranda);
        }
    }
    public boolean onCreateOptionMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Lakukan query disini
                textView.setText("Hasil Pencarian Query :" + query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU, bottomNavigationView.getSelectedItemId());
    }


}