
//Validation

//Första körningen fungerar inte i firefox men verkar fungera i chrome
$("#addTeacher, #etf, #asf, #acf, #ecf, #acsf, #ecsf, #login-form").validate({
    highlight: function (element, errorClass) {
        $(element).closest(".form-group").addClass("has-error");
    },
    unhighlight: function (element, errorClass) {
        $(element).closest(".form-group").removeClass("has-error");
    }
});


    
$(document).ready(function () {

    $(".theCarousel").carousel();
    
    $('[data-toggle="tooltip"]').tooltip(); 

    (function () {
        $(".pdsa-sn-wrapper ul li span").on("click", function (event) {
            $(".pdsa-sn-wrapper ul li span").removeClass("choosedElem");
            $(this).addClass("choosedElem");
        });
    })();

    //Teachers functionality
    (function () {

        //Todo find a better way to hide the teacher info panels
        $(".teacherInfo").hide();

        $(".removeTeacher").on("click", function (event) {
            var email = $(this).data("email");
            if (confirm("Are you sure you want to remove " + email)) {
                $("#removeTeacherValue").val(email);
                $('form[name="removeTeacherForm"]').submit();
            }
            event.preventDefault();
        });


        //On closing the teacher-form
        $("#myModal").on("hidden.bs.modal", function (event) {
            //Clears the teacher-form text-fields
            $("#teacherForm input[type='text']").val("");
            //Clear the teacher-form password field
            $("#teacherForm input[type='password']").val("");
        });

        //On showing the teacher-form
        $('#myModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);

            //Prepare form for adding new teacher
            if (button.prop("name") === "add") {
                $("#passwordFormGroup").show();
            }
            //Prepare form for editing existing teacher 
            else {
                //fillEditForm(button);
                $("#passwordFormGroup").hide();
            }
        });

        $("#closeTeacherInfo").on("click", function (event) {
            $("#teacherInfo").hide();
            event.preventDefault();
        });

    })();


    //Courses functionality
    (function () {

        $('#addCourseModal').on('show.bs.modal', function (event) {
            //Clears the course form text-fields
            $("#acf input[type='text']").val("");
            //Sets the first radio button to checked
            $("#acf input[type='radio']").first().prop("checked", true);
        });

        //Remove a course by invoking the CourseServlet
        $(".removeCourse").on("click", function (event) {
            var courseCode = $(this).data("coursecode");
            if (confirm("Are you sure you want to remove " + courseCode)) {
                $("#removeCourseValue").val(courseCode); //Value to send to the course servlet
                $('form[name="removeCourseForm"]').submit();
            }
            event.preventDefault();
        });


        //On closing the course-form
        $("#myCourseModal").on("hidden.bs.modal", function (event) {
            //Clears the course-form text-fields
            $("#courseForm input[type='text']").val("");

            //Clears radio-button settings and sets the basic level
            $("label[id^='label_']").removeClass("active");
            $("#label_basic").addClass("active");
            $("input[id^='radio_']").removeAttr("checked");
            $("#radio_basic").attr("checked", "checked");
        });

        //Show semester info
        $(".showSemester").on("click", function (event) {
            $("#courseSemester").hide();
        });

    })();


    //Students functionality
    (function () {

        //On showing the student-form
        $('#myStudentModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);

            //Prepare form for adding new teacher
            if (button.prop("name") === "add") {
                $("#passwordFormGroup").show();
            }
            //Prepare form for editing existing teacher 
            else {
                $("#passwordFormGroup").hide();
            }
        });


        $("#closeStudentInfo").on("mouseout", function (event) {
            $("#studentInfo").hide();
            event.preventDefault();
        });

    })();

});

function showTeacherInfoPanel() {
    $("#teacherInfo").show();
}

function hideStudentInfo(){
    $("#studentInfo").hide();
}

function showStudentInfoPanel(event) {
    var left = event.pageX;
    var top = event.pageY;
    $("#studentInfo").css("left", left);
    $("#studentInfo").css("top", top);
    $("#studentInfo").show();
}

function toggleAbsenceRowLook(event){
    var checkBox = $(event.target);
    checkBox.parent('td').closest('.studentRows').toggleClass('danger');
}


