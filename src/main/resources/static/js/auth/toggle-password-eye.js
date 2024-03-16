const fields = ["password", "confirm-password"]
const eyes = ["password-eye", "confirm-password-eye"]

for (let i = 0; i < fields.length; i++) {
    const passwordField = document.getElementById(fields[i]);
    const togglePassword = document.getElementById(eyes[i]);

    $("#" + eyes[i]).on("mousedown mouseup", function (e) {
        if (e.type === "mousedown") {
            passwordField.type = "text";
            togglePassword.innerText = "visibility_off";
        } else {
            passwordField.type = "password";
            togglePassword.innerText = "visibility";
        }
    });
}