package com.example.fragment_list_to_do

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment_to_do.FragmentToDo
import com.example.projecttodo.ToDoData
import com.example.projecttodo.R
import java.util.*


const val KEY_ID = "myCrimeId"
class FragmentListToDo : Fragment() {


    private lateinit var toDoRecycleView: RecyclerView // 1 dec


    val toDoListViewModel by lazy { ViewModelProvider(this).get(listViewModel::class.java) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListViewModel.liveDataTask.observe(
            viewLifecycleOwner, Observer {
                updateAdapter(it)

            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.addmenu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_menu -> {
                val task = ToDoData()
                toDoListViewModel.addTasks(task)

                val args = Bundle()
                args.putSerializable(KEY_ID, task.id)
                val fragment = FragmentToDo()
                fragment.arguments = args

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_list_to_do, container, false)
        toDoRecycleView = view.findViewById(R.id.recycler_view_list)
        val linearLayoutManager = LinearLayoutManager(context)
        toDoRecycleView.layoutManager = linearLayoutManager // نرتيب راسي للعناصر
        return view

    }

    fun updateAdapter(task: List<ToDoData>) {
        val taskAdapter = ToDoAdapter(task)
        toDoRecycleView.adapter = taskAdapter
    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {


        private lateinit var task: ToDoData
        private val titleTextView: TextView = itemView.findViewById(R.id.title_item)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_item)
        private val deleteImg: ImageView = itemView.findViewById(R.id.date_img)
        private val toDate: TextView = itemView.findViewById(R.id.to_date)
        private val timeOutImg: ImageView = itemView.findViewById(R.id.time_out1)
        private val smileImg : ImageView = itemView.findViewById(R.id.smile_img)
        private val shareImg : ImageView = itemView.findViewById(R.id.share_img)

        init {
            deleteImg.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }



        private val dateFormat = "yyyy / MM / dd"

        private fun getTaskReport():String{
            val dateString = DateFormat.format(dateFormat,task.date)
            return "Hey my friend ! I want to share with you my task on $dateString , my task is ${task.title} ,${task.detailsEditTxte}"

        }

        fun bind(task: ToDoData) {
            this.task = task
            titleTextView.text = task.title
            dateTextView.text = task.date.toString()
            val currentDay = Date()
            timeOutImg.visibility = if (currentDay.after(task.date)) {
                View.VISIBLE
            } else  {
                View.GONE
            }
            smileImg.visibility = if (currentDay.before(task.date)){
                View.VISIBLE
            }else{
                View.GONE
            }




            if (currentDay.after(task.date)) {
                    toDate.setTextColor(Color.RED)
                    toDate.text = "Time of this task is over" }
             else if (currentDay.before(task.date)) {
               toDate.setTextColor(Color.GRAY)
                toDate.text = " Great! you have time to do it"


            }



            shareImg.setOnClickListener {

                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                   putExtra(Intent.EXTRA_TEXT,getTaskReport())
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Task Report")
                }.also {
                    val chooserIntent =
                        Intent.createChooser(it," send_report")
                    startActivity(chooserIntent)
                }

            }
    }




        override fun onClick(v: View?) {

            if (v == deleteImg){
               toDoListViewModel.deleteTasks(task)
            }

            if (v == itemView){
            val fragment =FragmentToDo()
              val args = Bundle ()
                args.putSerializable(KEY_ID , task.id)
                fragment.arguments = args
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .addToBackStack(null)
                    .commit()
            }
}

        }

    }


    private inner class ToDoAdapter (var tasks :List <ToDoData> ):RecyclerView.Adapter<TaskHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
val view = layoutInflater.inflate(R.layout.list_item_to_do,parent,false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]

            holder.bind(task)

        }

        override fun getItemCount(): Int = tasks.size


    }






}