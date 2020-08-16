package com.nurzainpradana.koperasimasjid.view.main;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.view.home.HomeFragment;
import com.nurzainpradana.koperasimasjid.view.favorite.FavoriteFragment;
import com.nurzainpradana.koperasimasjid.view.cart.CartFragment;
import com.nurzainpradana.koperasimasjid.view.profile.ProfileFragment;
import com.nurzainpradana.koperasimasjid.view.transaction.TransactionFragment;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    //BottomNavBar

    BottomNavigationView bottomNavigationView;

    Bundle mBundle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            if(item.getItemId() == R.id.navigation_home){
                fragment = HomeFragment.newInstance();
                fragment.setArguments(mBundle);
            } else if(item.getItemId() == R.id.navigation_transaction){
                fragment = TransactionFragment.newInstance();
                fragment.setArguments(mBundle);
            } else if(item.getItemId() == R.id.navigation_keranjang){
                fragment = CartFragment.newInstance();
                fragment.setArguments(mBundle);
                fragment.setArguments(mBundle);
            } else if(item.getItemId() == R.id.navigation_favorite){
                fragment = FavoriteFragment.newInstance();
                fragment.setArguments(mBundle);
            } else if(item.getItemId() == R.id.navigation_profile){
                fragment = ProfileFragment.newInstance();
                fragment.setArguments(mBundle);
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
            savedInstanceState.getInt(Const.SELECTED_MENU);
        } else {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
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
        outState.putInt(Const.SELECTED_MENU, bottomNavigationView.getSelectedItemId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}