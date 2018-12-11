package com.kkinfosis.gamingwallpaper4k.activities;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.kkinfosis.gamingwallpaper4k.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends ListFragment implements OnQueryTextListener, OnActionExpandListener {
    private ArrayAdapter<String> mAdapter;
    List<String> mAllValues;
    private Context mContext;

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String str);
    }

    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }

    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    public boolean onQueryTextSubmit(String str) {
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        setHasOptionsMenu(true);
        populateList();
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        String str = (String) listView.getAdapter().getItem(i);
        if (getActivity() instanceof OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).OnItem1SelectedListener(str);
        }
        getFragmentManager().popBackStack();
    }

    public void onDetach() {
        super.onDetach();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.search_fragment, viewGroup, false);
        return inflate;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, menuInflater);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onQueryTextChange(String str) {
        if (str == null || str.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        List arrayList = new ArrayList(this.mAllValues);
        for (String str2 : this.mAllValues) {
            if (!str2.toLowerCase().contains(str.toLowerCase())) {
                arrayList.remove(str2);
            }
        }
        this.mAdapter = new ArrayAdapter(this.mContext, android.R.layout.simple_list_item_1, arrayList);
        setListAdapter(this.mAdapter);
        return false;
    }

    public void resetSearch() {
        this.mAdapter = new ArrayAdapter(this.mContext, android.R.layout.simple_list_item_1, this.mAllValues);
        setListAdapter(this.mAdapter);
    }

    private void populateList() {
        this.mAllValues = new ArrayList();
        this.mAllValues.add("Afghanistan");
        this.mAllValues.add("Åland Islands");
        this.mAllValues.add("Albania");
        this.mAllValues.add("Algeria");
        this.mAllValues.add("American Samoa");
        this.mAllValues.add("AndorrA");
        this.mAllValues.add("Angola");
        this.mAllValues.add("Anguilla");
        this.mAllValues.add("Antarctica");
        this.mAllValues.add("Antigua and Barbuda");
        this.mAllValues.add("Argentina");
        this.mAllValues.add("Armenia");
        this.mAllValues.add("Aruba");
        this.mAllValues.add("Australia");
        this.mAllValues.add("Austria");
        this.mAllValues.add("Azerbaijan");
        this.mAllValues.add("Bahamas");
        this.mAllValues.add("Bahrain");
        this.mAllValues.add("Bangladesh");
        this.mAllValues.add("Barbados");
        this.mAllValues.add("Belarus");
        this.mAllValues.add("Belgium");
        this.mAllValues.add("Belize");
        this.mAllValues.add("Benin");
        this.mAllValues.add("Bermuda");
        this.mAllValues.add("Bhutan");
        this.mAllValues.add("Bolivia");
        this.mAllValues.add("Bosnia and Herzegovina");
        this.mAllValues.add("Botswana");
        this.mAllValues.add("Bouvet Island");
        this.mAllValues.add("Brazil");
        this.mAllValues.add("British Indian Ocean Territory");
        this.mAllValues.add("Brunei Darussalam");
        this.mAllValues.add("Bulgaria");
        this.mAllValues.add("Burkina Faso");
        this.mAllValues.add("Burundi");
        this.mAllValues.add("Cambodia");
        this.mAllValues.add("Cameroon");
        this.mAllValues.add("Canada");
        this.mAllValues.add("Cape Verde");
        this.mAllValues.add("Cayman Islands");
        this.mAllValues.add("Central African Republic");
        this.mAllValues.add("Chad");
        this.mAllValues.add("Chile");
        this.mAllValues.add("China");
        this.mAllValues.add("Christmas Island");
        this.mAllValues.add("Cocos (Keeling) Islands");
        this.mAllValues.add("Colombia");
        this.mAllValues.add("Comoros");
        this.mAllValues.add("Congo");
        this.mAllValues.add("Congo, The Democratic Republic of the");
        this.mAllValues.add("Cook Islands");
        this.mAllValues.add("Costa Rica");
        this.mAllValues.add("Cote D'Ivoire");
        this.mAllValues.add("Croatia");
        this.mAllValues.add("Cuba");
        this.mAllValues.add("Cyprus");
        this.mAllValues.add("Czech Republic");
        this.mAllValues.add("Denmark");
        this.mAllValues.add("Djibouti");
        this.mAllValues.add("Dominica");
        this.mAllValues.add("Dominican Republic");
        this.mAllValues.add("Ecuador");
        this.mAllValues.add("Egypt");
        this.mAllValues.add("El Salvador");
        this.mAllValues.add("Equatorial Guinea");
        this.mAllValues.add("Eritrea");
        this.mAllValues.add("Estonia");
        this.mAllValues.add("Ethiopia");
        this.mAllValues.add("Falkland Islands (Malvinas)");
        this.mAllValues.add("Faroe Islands");
        this.mAllValues.add("Fiji");
        this.mAllValues.add("Finland");
        this.mAllValues.add("France");
        this.mAllValues.add("French Guiana");
        this.mAllValues.add("French Polynesia");
        this.mAllValues.add("French Southern Territories");
        this.mAllValues.add("Gabon");
        this.mAllValues.add("Gambia");
        this.mAllValues.add("Georgia");
        this.mAllValues.add("Germany");
        this.mAllValues.add("Ghana");
        this.mAllValues.add("Gibraltar");
        this.mAllValues.add("Greece");
        this.mAllValues.add("Greenland");
        this.mAllValues.add("Grenada");
        this.mAllValues.add("Guadeloupe");
        this.mAllValues.add("Guam");
        this.mAllValues.add("Guatemala");
        this.mAllValues.add("Guernsey");
        this.mAllValues.add("Guinea");
        this.mAllValues.add("Guinea-Bissau");
        this.mAllValues.add("Guyana");
        this.mAllValues.add("Haiti");
        this.mAllValues.add("Heard Island and Mcdonald Islands");
        this.mAllValues.add("Holy See (Vatican City State)");
        this.mAllValues.add("Honduras");
        this.mAllValues.add("Hong Kong");
        this.mAllValues.add("Hungary");
        this.mAllValues.add("Iceland");
        this.mAllValues.add("India");
        this.mAllValues.add("Indonesia");
        this.mAllValues.add("Iran, Islamic Republic Of");
        this.mAllValues.add("Iraq");
        this.mAllValues.add("Ireland");
        this.mAllValues.add("Isle of Man");
        this.mAllValues.add("Israel");
        this.mAllValues.add("Italy");
        this.mAllValues.add("Jamaica");
        this.mAllValues.add("Japan");
        this.mAllValues.add("Jersey");
        this.mAllValues.add("Jordan");
        this.mAllValues.add("Kazakhstan");
        this.mAllValues.add("Kenya");
        this.mAllValues.add("Kiribati");
        this.mAllValues.add("Korea, Democratic People'S Republic of");
        this.mAllValues.add("Korea, Republic of");
        this.mAllValues.add("Kuwait");
        this.mAllValues.add("Kyrgyzstan");
        this.mAllValues.add("Lao People'S Democratic Republic");
        this.mAllValues.add("Latvia");
        this.mAllValues.add("Lebanon");
        this.mAllValues.add("Lesotho");
        this.mAllValues.add("Liberia");
        this.mAllValues.add("Libyan Arab Jamahiriya");
        this.mAllValues.add("Liechtenstein");
        this.mAllValues.add("Lithuania");
        this.mAllValues.add("Luxembourg");
        this.mAllValues.add("Macao");
        this.mAllValues.add("Macedonia, The Former Yugoslav Republic of");
        this.mAllValues.add("Madagascar");
        this.mAllValues.add("Malawi");
        this.mAllValues.add("Malaysia");
        this.mAllValues.add("Maldives");
        this.mAllValues.add("Mali");
        this.mAllValues.add("Malta");
        this.mAllValues.add("Marshall Islands");
        this.mAllValues.add("Martinique");
        this.mAllValues.add("Mauritania");
        this.mAllValues.add("Mauritius");
        this.mAllValues.add("Mayotte");
        this.mAllValues.add("Mexico");
        this.mAllValues.add("Micronesia, Federated States of");
        this.mAllValues.add("Moldova, Republic of");
        this.mAllValues.add("Monaco");
        this.mAllValues.add("Mongolia");
        this.mAllValues.add("Montserrat");
        this.mAllValues.add("Morocco");
        this.mAllValues.add("Mozambique");
        this.mAllValues.add("Myanmar");
        this.mAllValues.add("Namibia");
        this.mAllValues.add("Nauru");
        this.mAllValues.add("Nepal");
        this.mAllValues.add("Netherlands");
        this.mAllValues.add("Netherlands Antilles");
        this.mAllValues.add("New Caledonia");
        this.mAllValues.add("New Zealand");
        this.mAllValues.add("Nicaragua");
        this.mAllValues.add("Niger");
        this.mAllValues.add("Nigeria");
        this.mAllValues.add("Niue");
        this.mAllValues.add("Norfolk Island");
        this.mAllValues.add("Northern Mariana Islands");
        this.mAllValues.add("Norway");
        this.mAllValues.add("Oman");
        this.mAllValues.add("Pakistan");
        this.mAllValues.add("Palau");
        this.mAllValues.add("Palestinian Territory, Occupied");
        this.mAllValues.add("Panama");
        this.mAllValues.add("Papua New Guinea");
        this.mAllValues.add("Paraguay");
        this.mAllValues.add("Peru");
        this.mAllValues.add("Philippines");
        this.mAllValues.add("Pitcairn");
        this.mAllValues.add("Poland");
        this.mAllValues.add("Portugal");
        this.mAllValues.add("Puerto Rico");
        this.mAllValues.add("Qatar");
        this.mAllValues.add("Reunion");
        this.mAllValues.add("Romania");
        this.mAllValues.add("Russian Federation");
        this.mAllValues.add("RWANDA");
        this.mAllValues.add("Saint Helena");
        this.mAllValues.add("Saint Kitts and Nevis");
        this.mAllValues.add("Saint Lucia");
        this.mAllValues.add("Saint Pierre and Miquelon");
        this.mAllValues.add("Saint Vincent and the Grenadines");
        this.mAllValues.add("Samoa");
        this.mAllValues.add("San Marino");
        this.mAllValues.add("Sao Tome and Principe");
        this.mAllValues.add("Saudi Arabia");
        this.mAllValues.add("Senegal");
        this.mAllValues.add("Serbia and Montenegro");
        this.mAllValues.add("Seychelles");
        this.mAllValues.add("Sierra Leone");
        this.mAllValues.add("Singapore");
        this.mAllValues.add("Slovakia");
        this.mAllValues.add("Slovenia");
        this.mAllValues.add("Solomon Islands");
        this.mAllValues.add("Somalia");
        this.mAllValues.add("South Africa");
        this.mAllValues.add("South Georgia and the South Sandwich Islands");
        this.mAllValues.add("Spain");
        this.mAllValues.add("Sri Lanka");
        this.mAllValues.add("Sudan");
        this.mAllValues.add("Suriname");
        this.mAllValues.add("Svalbard and Jan Mayen");
        this.mAllValues.add("Swaziland");
        this.mAllValues.add("Sweden");
        this.mAllValues.add("Switzerland");
        this.mAllValues.add("Syrian Arab Republic");
        this.mAllValues.add("Taiwan, Province of China");
        this.mAllValues.add("Tajikistan");
        this.mAllValues.add("Tanzania, United Republic of");
        this.mAllValues.add("Thailand");
        this.mAllValues.add("Timor-Leste");
        this.mAllValues.add("Togo");
        this.mAllValues.add("Tokelau");
        this.mAllValues.add("Tonga");
        this.mAllValues.add("Trinidad and Tobago");
        this.mAllValues.add("Tunisia");
        this.mAllValues.add("Turkey");
        this.mAllValues.add("Turkmenistan");
        this.mAllValues.add("Turks and Caicos Islands");
        this.mAllValues.add("Tuvalu");
        this.mAllValues.add("Uganda");
        this.mAllValues.add("Ukraine");
        this.mAllValues.add("United Arab Emirates");
        this.mAllValues.add("United Kingdom");
        this.mAllValues.add("United States");
        this.mAllValues.add("United States Minor Outlying Islands");
        this.mAllValues.add("Uruguay");
        this.mAllValues.add("Uzbekistan");
        this.mAllValues.add("Vanuatu");
        this.mAllValues.add("Venezuela");
        this.mAllValues.add("Viet Nam");
        this.mAllValues.add("Virgin Islands, British");
        this.mAllValues.add("Virgin Islands, U.S.");
        this.mAllValues.add("Wallis and Futuna");
        this.mAllValues.add("Western Sahara");
        this.mAllValues.add("Yemen");
        this.mAllValues.add("Zambia");
        this.mAllValues.add("Zimbabwe");
        this.mAdapter = new ArrayAdapter(this.mContext, android.R.layout.simple_list_item_1, this.mAllValues);
        setListAdapter(this.mAdapter);
    }
}
