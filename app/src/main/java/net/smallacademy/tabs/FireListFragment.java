package net.smallacademy.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FireListFragment extends Fragment {
    ListView fireList;
    String mTitle[] = {
            "Bousbaa Moussa,Didouche Mourad,Constantine",
            "UV15 nouvell ville,El khroub ,Constantine",
            "Sidi Mabrouk,Constantine",
            "Bousbaa Moussa,Didouche Mourad,Constantine",
            "UV15 nouvell ville,El khroub ,Constantine",
            "Sidi Mabrouk,Constantine",
    };
    String mDescription[] = {
            "By: @ouail_nazim (0540037061)",
            "By: @kira_oussama (0554795175)",
            "By: @lina_djaalb (07503461)","By: @ouail_nazim (0540037061)",
            "By: @kira_oussama (0554795175)",
            "By: @lina_djaalb (07503461)",
    };
    int status[] = {1,2,3,1,2,3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFiresList();
    }

    private void getFiresList() {
        fireList = getActivity().findViewById(R.id.fire_List);
        ListAdapter adapter = new ListAdapter(getContext(), mTitle, status, mDescription);
        fireList.setAdapter(adapter);
        fireList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Show Fire Num : " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_fire_list, container, false);

    }
}