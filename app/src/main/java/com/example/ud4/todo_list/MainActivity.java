package com.example.ud4.todo_list;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.graphics.Paint;

public class MainActivity extends AppCompatActivity 
{
    //Data members
    ArrayList<ListItem> listItems = new ArrayList<ListItem>();
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView's array
        // *********************************** 
        listItems.add(new ListItem("Finish tutorial"));
        listItems.add(new ListItem("Finish app"));


        // Set adapter
        adapter = new ItemAdapter(this, listItems);
        ListView listView = (ListView) findViewById(R.id.incomplete_list);
        listView.setAdapter(adapter);

        // Item Long Click -- Edit item
        // ********************************************* 
        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id)
            {
                // Cast currentView
                CheckBox currentView = (CheckBox) view;
                final String originalText = currentView.getText().toString();

                //Pop open a dialog with EditText
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit Item:");
                
                // Set up the input
                final EditText input = new EditText(MainActivity.this);

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(originalText);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        String grabbedText = input.getText().toString();

                        //Find index of edited value in the ArrayList
                        for (int i = 0; i < listItems.size(); ++i)
                        {
                            if (listItems.get(i).getText() == originalText)
                            {
                                listItems.get(i).setText(grabbedText);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                // Set up the buttons
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();

                return true;
            }//end-onItemLongClick
        });

        // Item OnClick -- toggleStrikeThrough 
        // ********************************************* 
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) 
            {
                //Grab checkBox (the listView item)
                CheckBox checkbox = (CheckBox) view;
                
                //Determine if checked
                if (checkbox.isChecked() == true)
                {
                    //Set Strikethrough text
                    checkbox.setPaintFlags(checkbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    listItems.get(position).setFlag(true);
                }
                else
                {
                    //Reset strikethrough
                    checkbox.setPaintFlags(checkbox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    listItems.get(position).setFlag(false);
                }
            }//end-onItemClick
        });

        // Add Button
        // *********************************** 
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
        
            public void onClick(View view)
            {
                //Pop up a dialog with an entry box
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter new item:");
                
                // Set up the input
                final EditText input = new EditText(MainActivity.this);

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        //Create NEW ListView item
                        //Set text to m_Text
                        //Append to incomplete
                        
                        String grabbedText = input.getText().toString();
                        ListItem newItem = new ListItem(grabbedText);
                        listItems.add(newItem);                
                        adapter.notifyDataSetChanged();
                    }
                });

                // Set up the buttons
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
            }
        });
    }//end-onCreate
}//end-class
