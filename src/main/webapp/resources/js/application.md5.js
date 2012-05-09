/*
 * Application function for working with password fields
 *
 */
function updatePasswordEncodingById(unencodedPasswCompId, encodedPassCompId)
{
    var unencodedTextField = document.getElementById(unencodedPasswCompId);
    var encodedTextField = document.getElementById(encodedPassCompId);
    var currentUnencoded = unencodedTextField.value;
    var newEncoded = hex_md5(currentUnencoded);
    encodedTextField.value = newEncoded;
}
