package com.assesment.imagepickerapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.assesment.imagepickerapplication.databinding.FragmentFirstBinding
import com.assesment.imagepickerapplication.util.arePermissionsGranted
import com.assesment.imagepickerapplication.util.callRequestPermission
import com.assesment.imagepickerapplication.util.checkCameraPermission
import com.assesment.imagepickerapplication.util.shouldRequestPermissionsAtRuntime
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yalantis.ucrop.UCrop
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fromCamera.setOnClickListener {
            if (context?.shouldRequestPermissionsAtRuntime()==true && context?.arePermissionsGranted()==false) {
                context?.callRequestPermission()
            } else {
                findNavController().navigate(R.id.action_FirstFragment_to_cameraFragment)
            }
        }


        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data2= result.data
                val outputDirectory = File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "assessment_demo.jpg"
                )
                //doSomeOperations()
                UCrop.of(data2!!.data!!, Uri.fromFile(outputDirectory))
                    .withAspectRatio(16F, 9F)
                    .start(requireActivity());
            }
        }

        binding.fromGallery.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch(Intent.createChooser(i, "Select Picture"))

        }


    }

    fun openSomeActivityForResult() {

    }

    override fun onResume() {
        super.onResume()
        val outputDirectory = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "assessment_demo.jpg"
        )
        if(outputDirectory.exists())
        {
            Glide.with(requireActivity())
                .load(outputDirectory)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageView)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}