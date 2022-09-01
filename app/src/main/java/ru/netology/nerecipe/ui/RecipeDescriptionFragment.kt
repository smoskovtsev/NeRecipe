package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeDescriptionFragmentBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class RecipeDescriptionFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>()

    private val initialRecipe by lazy {
        val args by navArgs<RecipeDescriptionFragmentArgs>()
        args.initialRecipe
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeDescriptionFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.editName.editText!!.setText(initialRecipe?.name)
        binding.editName.requestFocus()
        binding.editAuthor.editText?.setText(initialRecipe?.author)
        binding.editCategory.editText?.setText(initialRecipe?.category)
        binding.editDescription.editText!!.setText(initialRecipe?.description)
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }

        val items = resources.getStringArray(R.array.cuisine_types)
        val adapter = ArrayAdapter(requireContext(), R.layout.category_list_item, items)
        (binding.editCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }.root

    private fun onOkButtonClicked(binding: RecipeDescriptionFragmentBinding) {
        val textName = binding.editName.editText?.text
        val textAuthor = binding.editAuthor.editText?.text
        val textCategory = binding.editCategory.editText?.text
        val textDescription = binding.editDescription.editText?.text
        if (!textName.isNullOrBlank() && !textDescription.isNullOrBlank() ) {
            val resultBundle = Bundle(1)
            resultBundle.putStringArray(RESULT_KEY, arrayOf(textName.toString(), textAuthor.toString(), textCategory.toString(), textDescription.toString()))
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "resultKey"
    }
}