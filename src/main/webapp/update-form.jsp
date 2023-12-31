<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Edit student details</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<div class="card" style="width: 30rem; margin: 100px auto">
	<h2>Student registration form</h2>
	<div style="width: 28rem;">

		<form action="StudentController">
			<input type="hidden" name="cmd" value="UPDATE"> <input
				type="hidden" name="sid" value="${THE_STUDENT.sid }">


			<div class="mb-3">
				<label class="form-label">First Name </label> <input
					class="form-control" name="Fn" value="${THE_STUDENT.firstname }">
			</div>

			<div class="mb-3">
				<label class="form-label">Last name</label> <input
					class="form-control" name="Ln" value="${THE_STUDENT.lastname }">
			</div>

			<div class="mb-3">
				<label class="form-label">Email address</label> <input
					class="form-control" name="Email" value="${THE_STUDENT.email }">
			</div>

			<div class="mb-3">
				<button class="btn btn-primary">Update Student</button>
			</div>
		</form>
	</div>
	</div>
</body>

</html>