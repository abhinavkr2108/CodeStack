package com.example.codestack.ui.fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.codestack.R
import com.example.codestack.databinding.FragmentResumeBinding
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.GrooveBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder.MultiPermissionListener
import com.karumi.dexter.DexterBuilder.Permission
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream



class ResumeFragment : Fragment() {
    private lateinit var resumeBinding: FragmentResumeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_resume, container, false)

        resumeBinding = FragmentResumeBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resumeBinding.btnResume.setOnClickListener {
            checkStoragePermission()
        }

    }

    private fun checkStoragePermission(){
        Dexter.withContext(requireContext())
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    p0.let {
                        if (p0 != null) {
                            if (p0.areAllPermissionsGranted()==true){
                                createResume()
                            } else{
                                Toast.makeText(requireContext(), "Please Grant All Permissions", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                   p1?.continuePermissionRequest()
                }
            }).check()

    }
    private fun createResume() {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val currentTime = System.currentTimeMillis().toString()
        val child = "$currentTime.pdf"
        val file = File(pdfPath,child)
        val outputStream = FileOutputStream(file)

        // iText- Pdf
        val pdfWriter = PdfWriter(file)
        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        //creating PDF
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(10f, 10f, 10f, 10f)

        // Adding Image
//        val d = resources.getDrawable(R.drawable.ic_baseline_account_box_24).toString()
//        val bitmap = (BitmapDrawable(context?.resources,d)).getBitmap();
//        val stream = ByteArrayOutputStream();
//        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        val bitmapData = stream.toByteArray();
//        val imageData = ImageDataFactory.create(bitmapData)
//        val image = Image(imageData)

        // Adding User Name and Email
        val name = Paragraph(resumeBinding.personName.text.toString()).setBold().setFontSize(30f).setTextAlignment(TextAlignment.CENTER)
        val email = Paragraph(resumeBinding.yourEmail.text.toString()).setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER)

        //Adding Table
        val introTableWidth = floatArrayOf(PageSize.A4.width)
        val introTable = Table(introTableWidth)
        val introTableBorder = GrooveBorder(DeviceRgb(255,255,255),2f)
        introTable.setHorizontalAlignment(HorizontalAlignment.LEFT)
        introTable.setMarginTop(15f)

        // Table Cells: Introduction
        introTable.addCell(Cell().setBackgroundColor(ColorConstants.BLUE).setBorder(introTableBorder).add(Paragraph("Introduction").setFontColor(ColorConstants.WHITE).setFontSize(20f)))
        introTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(resumeBinding.introText.text.toString()).setFontSize(20f)))

        //Table Cells: Education
        val educationTableWidth = floatArrayOf(PageSize.A4.width)
        val educationTable = Table(educationTableWidth)
        educationTable.setMarginTop(15f)
        educationTable.addCell(Cell().setBackgroundColor(ColorConstants.BLUE).setBorder(introTableBorder).add(Paragraph("Eduction Details").setFontSize(20f).setFontColor(ColorConstants.WHITE)))
        val educationDetailsTableWidth = floatArrayOf(PageSize.A4.width)
        val educationDetailsTable = Table(educationDetailsTableWidth)
        // Class 10th Details
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("Middle School: ${resumeBinding.middleSchool.text}").setFontSize(20f).setBold()))
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("Secured ${resumeBinding.middleSchoolPercentage.text}% in Class 10th").setFontSize(20f)))
        // Class 12th Details
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("High School: ${resumeBinding.highSchool.text}").setFontSize(20f).setBold()))
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("Secured ${resumeBinding.highSchoolPercentage.text}% in Class 12th").setFontSize(20f)))
        // College Details
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("College: ${resumeBinding.highSchool.text}").setFontSize(20f).setBold()))
        educationDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph("Secured aggregate ${resumeBinding.highSchoolPercentage.text} CGPA in college").setFontSize(20f)))

        // Table Cells: Experience
        val experienceTableWidth = floatArrayOf(PageSize.A4.width)
        val experienceTable = Table(experienceTableWidth)
        experienceTable.setMarginTop(15f)
        experienceTable.addCell(Cell().setBackgroundColor(ColorConstants.BLUE).setBorder(introTableBorder).add(Paragraph("Experience").setFontSize(20f).setFontColor(ColorConstants.WHITE)))
        val expDetailsTableWidth = floatArrayOf(PageSize.A4.width)
        val expDetailsTable = Table(expDetailsTableWidth)
        // First Company
        expDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.companyName.text} - ${resumeBinding.companyPosition.text}").setFontSize(20f).setBold()))
        expDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.companyExp.text}").setFontSize(16f)))
        // Second Company
        expDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.company2Name.text} - ${resumeBinding.company2Position.text}").setFontSize(20f).setBold()))
        expDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.company2Exp.text}").setFontSize(16f)))

        // Table Cells: Projects
        val projectTableWidth = floatArrayOf(PageSize.A4.width)
        val projectTable = Table(projectTableWidth)
        projectTable.setMarginTop(15f)
        projectTable.addCell(Cell().setBackgroundColor(ColorConstants.BLUE).setBorder(introTableBorder).add(Paragraph("Projects").setFontSize(20f).setFontColor(ColorConstants.WHITE)))
        val projectDetailsTableWidth = floatArrayOf(PageSize.A4.width)
        val projectDetailsTable = Table(projectDetailsTableWidth)
        // First Project
        projectDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.projectName.text} - ${resumeBinding.project2Code.text}").setFontSize(20f).setBold()))
        projectDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.projectDesc.text}").setFontSize(16f)))
        // Second Project
        projectDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.project2Name.text} - ${resumeBinding.project2Code.text}").setFontSize(20f).setBold()))
        projectDetailsTable.addCell(Cell().setBorder(introTableBorder).add(Paragraph(" ${resumeBinding.project2Desc.text}").setFontSize(16f)))

        document.add(name)
        document.add(email)
        document.add(introTable)
        document.add(educationTable)
        document.add(educationDetailsTable)
        document.add(experienceTable)
        document.add(expDetailsTable)
        document.add(projectTable)
        document.add(projectDetailsTable)


        // Close the document
        document.close()
        Toast.makeText(context, "PDF Downloaded", Toast.LENGTH_SHORT).show()


    }


}