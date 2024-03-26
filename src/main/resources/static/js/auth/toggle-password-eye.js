const fields = ["password", "confirm-password"]
const eyes = ["password-eye", "confirm-password-eye"]

for (let i = 0; i < fields.length; i++) {
    const passwordField = document.getElementById(fields[i]);
    const togglePassword = document.getElementById(eyes[i]);

    $("#" + eyes[i]).on("mousedown mouseup", function (e) {
        if (e.type === "mousedown") {
            passwordField.type = "text";
            togglePassword.classList.remove('fa-eye');
            togglePassword.classList.add('fa-eye-slash');
        } else {
            passwordField.type = "password";
            togglePassword.classList.remove('fa-eye-slash');
            togglePassword.classList.add('fa-eye');
        }
    });
}