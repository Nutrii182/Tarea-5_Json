package com.example.nart1.tarea_5_json.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nart1.tarea_5_json.R;


public class BaseApiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_baseapi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView recyclerView = getActivity().findViewById (R.id.baseApiList);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener () {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.canScrollVertically (1)) {
                    Log.i (Utils.TAG, "Top");
                } else if (recyclerView.canScrollVertically (-1)) {
                    Log.i (Utils.TAG, "Bottom");
                }
            }
        });


        new Utils().getJSON (Utils.SW_API_PLANETS_URL)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<String>() {

                    String json = "";

                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(String value) {
                        json += value;
                    }

                    @Override
                    public void onComplete() {
                        parseJSON (json, recyclerView);
                    }
                });

    }

    void parseJSON (String json, RecyclerView recyclerView) {
        SWApiPlanetsHeader planets = new Gson().fromJson (json, SWApiPlanetsHeader.class);
        recyclerView.setAdapter (new BaseAdapter (planets.results));
    }
}

class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseApiViewHolder> {

    private ArrayList<SWApiPlanet> data;

    public BaseAdapter (ArrayList<SWApiPlanet> d) {
        data = d;
    }

    @Override
    public BaseApiViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item, parent, false);

        return new BaseApiViewHolder (view);
    }

    @Override
    public void onBindViewHolder (BaseApiViewHolder holder, int position) {
        SWApiPlanet planet = data.get (position);
        holder.setData (planet.name, planet.diameter, planet.population);
    }

    @Override
    public int getItemCount () {
        return data.size();
    }

    class BaseApiViewHolder extends RecyclerView.ViewHolder {
        TextView tvData1, tvData2, tvData3;

        BaseApiViewHolder (View itemView) {
            super (itemView);

            tvData1 = itemView.findViewById (R.id.tvData1);
            tvData2 = itemView.findViewById (R.id.tvData2);
            tvData3 = itemView.findViewById (R.id.tvData3);
        }


        void setData (String data1, String data2, String data3) {
            tvData1.setText (data1);
            tvData2.setText (data2);
            tvData3.setText (data3);
        }
    }

}
