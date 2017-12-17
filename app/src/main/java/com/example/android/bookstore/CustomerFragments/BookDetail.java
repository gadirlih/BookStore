package com.example.android.bookstore.CustomerFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.android.bookstore.CustomerMainScreen;
import com.example.android.bookstore.Model.Book;
import com.example.android.bookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView book_Name, book_Price, book_Description;
    ImageView book_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String bookId="";

    FirebaseDatabase db;
    DatabaseReference books;

    Book currentBook;

    public BookDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetail newInstance(String param1, String param2) {
        BookDetail fragment = new BookDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Firebase
        db = FirebaseDatabase.getInstance();
        books = db.getReference("Books");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        //Initialize view
        numberButton = (ElegantNumberButton) view.findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) view.findViewById(R.id.btnCart);

        book_Description = (TextView)view.findViewById(R.id.bdbook_description);
        book_Name = (TextView)view.findViewById(R.id.bdbook_title);
        book_Price = (TextView)view.findViewById(R.id.bdbook_price);
        book_Image = (ImageView)view.findViewById(R.id.img_book);

        collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        getDetailFood(CustomerMainScreen.bookId);

        return view;
    }

    private void getDetailFood(String bookId) {
        books.child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentBook =dataSnapshot.getValue(Book.class);

                //set Image
                Picasso.with(getActivity()).load(currentBook.getImage())
                        .into(book_Image);

                //collapsingToolbarLayout.setTitle(currentBook.getTitle());

                book_Price.setText(currentBook.getPrice());

                book_Name.setText(currentBook.getTitle());

                book_Description.setText("Author: " + currentBook.getAuthor() + "\n"
                        + "Year: " + currentBook.getYear() + "\n"
                        + "Category: " + currentBook.getCategory());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
