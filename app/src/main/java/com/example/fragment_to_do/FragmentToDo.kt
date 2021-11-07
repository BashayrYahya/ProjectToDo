package com.example.fragment_to_do

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.fragment_list_to_do.KEY_ID
import com.example.projecttodo.R
import com.example.projecttodo.ToDoData
import java.util.*


const val TASK_DATE_KEY = "taskDate"

class FragmentToDo : Fragment () , DatePickerDialogueFragment.DatePickerCallback {

    private lateinit var titleEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var isDoneCheckBox: CheckBox
    private lateinit var task: ToDoData
    private lateinit var detailsEditTxte : EditText
    private lateinit var calenderImg : ImageView


    private val fragmentViewModel by lazy { ViewModelProvider(this).get(toDoViewModel::class.java) }
    // dec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         task = ToDoData()
        val taskId = arguments?.getSerializable(KEY_ID) as UUID
        fragmentViewModel.loadTask(taskId)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_to_do, container, false)

        init(view)

        dateTextView.apply {
            text = task.date.toString()
        }

        return view
    }


    private fun init(view: View) {
        titleEditText = view.findViewById(R.id.to_doEditText)
        dateTextView = view.findViewById(R.id.date_img)
        isDoneCheckBox = view.findViewById(R.id.done_check_box)
        detailsEditTxte = view.findViewById(R.id.details_to_do)
        calenderImg = view.findViewById(R.id.calender_img)
    }


    override fun onStart() {
        super.onStart()
        calenderImg.setOnClickListener {
            val args = Bundle()
            args.putSerializable(TASK_DATE_KEY, task.date)
             DatePickerDialogueFragment().also {
                it.arguments = args
                it.setTargetFragment(this, 0)
                it.show(this.parentFragmentManager, "date Picker")
            }


        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // i will do nothing
            }

            override fun onTextChanged(w1: CharSequence?, p1: Int, p2: Int, p3: Int) {
                task.title = w1.toString()

            }

            override fun afterTextChanged(p0: Editable?) {
                // i will do nothing
            }

        }

        val detailsWatcher = object  : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // i will do nothing
            }

            override fun onTextChanged(w2: CharSequence?, start: Int, before: Int, count: Int) {
             task.detailsEditTxte = w2.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // i will do nothing
            }
        }

        titleEditText.addTextChangedListener(textWatcher)
        detailsEditTxte.addTextChangedListener(detailsWatcher)

        isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->

            task.isDone = isChecked

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.taskLiveData.observe(
            viewLifecycleOwner, {
                it?.let {
                    task = it

                    titleEditText.setText(it.title)
                    dateTextView.text = it.toDate.toString()
                    isDoneCheckBox.isChecked = it.isDone
                    detailsEditTxte.setText(it.detailsEditTxte)

                }


            }
        )


    }

    override fun onStop() {
        super.onStop()
        fragmentViewModel.updateTask(task)
    }

    override fun onDateSelected(date: Date) {
        task.date = date
        dateTextView.text = date.toString()
    }
}