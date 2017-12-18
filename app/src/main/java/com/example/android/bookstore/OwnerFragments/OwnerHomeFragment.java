package com.example.android.bookstore.OwnerFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.Common;
import com.example.android.bookstore.CustomerFragments.BookDetail;
import com.example.android.bookstore.CustomerFragments.HomeFragment;
import com.example.android.bookstore.CustomerMainScreen;
import com.example.android.bookstore.Interfaces.ItemClickListener;
import com.example.android.bookstore.Model.Book;
import com.example.android.bookstore.Model.Owner;
import com.example.android.bookstore.OwnerMainScreen;
import com.example.android.bookstore.R;
import com.example.android.bookstore.ViewHolder.BookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.hoang8f.widget.FButton;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OwnerHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OwnerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OwnerHomeFragment.OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    LinearLayout rootLayout;

    FirebaseDatabase db;
    DatabaseReference user_books_table_ref, books_table;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;

    FirebaseRecyclerAdapter<Book, BookViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    Uri saveUri;

    FloatingActionButton floatingActionButton;



    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerHomeFragment newInstance(String param1, String param2) {
        OwnerHomeFragment fragment = new OwnerHomeFragment();
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

        db = FirebaseDatabase.getInstance();
        user_books_table_ref = db.getReference(Common.currentOwner.getUsername().toLowerCase());
        books_table = db.getReference("Books");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_home, container, false);

        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floating_ab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddBookDialog();

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.custmer_recycler_book);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        rootLayout = (LinearLayout)view.findViewById(R.id.rootLayout);

        loadBookLis();

        materialSearchBar = (MaterialSearchBar)view.findViewById(R.id.csearchBar);
        materialSearchBar.setHint("Enter Book name");

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                /*if(!enabled){
                    recyclerView.setAdapter(adapter);
                }*/
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text);
                materialSearchBar.disableSearch();
                //materialSearchBar.setText("");

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        return view;
    }

    MaterialEditText edtTitle, edtAuthor, edtCategory, edtYear, edtPrice;
    FButton btnSelect, btnUpload;
    Book newBook;

    private void showAddBookDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Add new book");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View add_book_layout = layoutInflater.inflate(R.layout.add_new_book_layout, null);

        edtTitle = (MaterialEditText) add_book_layout.findViewById(R.id.edtTitle);
        edtAuthor = (MaterialEditText) add_book_layout.findViewById(R.id.edtAuthor);
        edtCategory = (MaterialEditText) add_book_layout.findViewById(R.id.edtCategory);
        edtYear = (MaterialEditText) add_book_layout.findViewById(R.id.edtYear);
        edtPrice = (MaterialEditText) add_book_layout.findViewById(R.id.edtPrice);
        btnSelect = (FButton) add_book_layout.findViewById(R.id.btnSelect);
        btnUpload = (FButton) add_book_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();

            }
        });

        alertDialog.setView(add_book_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);



        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();

                if(newBook != null){
                    books_table.push().setValue(newBook);
                    user_books_table_ref.push().setValue(newBook);

                    Snackbar.make(rootLayout,"New Book: " + newBook.getTitle() + " was added", Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        alertDialog.show();

    }

    private void chooseImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Common.PICK_IMAGE_REQUEST);

    }

    private void uploadImage(){

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/" + imageName);
        imageFolder.putFile(saveUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Uploaded!", Toast.LENGTH_SHORT).show();
                        imageFolder.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                newBook = new Book();
                                newBook.setTitle(edtTitle.getText().toString());
                                newBook.setAuthor((edtAuthor.getText().toString()));
                                newBook.setBookstore(Common.currentOwner.getUsername());
                                newBook.setCategory(edtCategory.getText().toString());
                                newBook.setYear(edtYear.getText().toString());
                                newBook.setPrice(edtPrice.getText().toString());
                                newBook.setImage(uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data!=null && data.getData() != null){
            saveUri = data.getData();
            btnSelect.setText("Image Selected!");
        }
    }

    private void startSearch(CharSequence text){

        searchAdapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(
                Book.class,
                R.layout.book_item,
                BookViewHolder.class,
                user_books_table_ref.orderByChild("title").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {

                viewHolder.book_title.setText(model.getTitle());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.book_image);
                final Book local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean islongClick) {

                        OwnerMainScreen.bookId = searchAdapter.getRef(position).getKey();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ownerFragmentLayout, new BookDetail()).addToBackStack(null).commit();

                    }
                });
            }
        };

        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest(){

        user_books_table_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Book item = postSnapshot.getValue(Book.class);
                    suggestList.add(item.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadBookLis(){

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(Book.class, R.layout.book_item,
                BookViewHolder.class,
                user_books_table_ref) {
            @Override
            protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {

                viewHolder.book_title.setText(model.getTitle());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.book_image);
                final Book local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean islongClick) {

                        OwnerMainScreen.bookId = adapter.getRef(position).getKey();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ownerFragmentLayout, new BookDetail()).addToBackStack(null).commit();

                    }
                });

            }
        };

        recyclerView.setAdapter(adapter);

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
        if (context instanceof OwnerHomeFragment.OnFragmentInteractionListener) {
            mListener = (OwnerHomeFragment.OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
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
