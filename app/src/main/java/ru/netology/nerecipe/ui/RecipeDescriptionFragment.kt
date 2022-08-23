package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
        binding.editName.setText(initialRecipe?.name)
        binding.editAuthor.setText(initialRecipe?.author)
        binding.editCategory.setText(initialRecipe?.category)
        binding.editDescription.setText(initialRecipe?.description)
        binding.editDescription.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }.root

    private fun onOkButtonClicked(binding: RecipeDescriptionFragmentBinding) {
        val textName = binding.editName.text
        val textAuthor = binding.editAuthor.text
        val textCategory = binding.editCategory.text
        val textDescription = binding.editDescription.text
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