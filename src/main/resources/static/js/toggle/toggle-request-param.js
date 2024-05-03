function goto(requestParamKey, requestParamValue){
    window.location.href = replaceQueryParam(requestParamKey, requestParamValue)
}

// Replace or add query param if existing or not, respectively
function replaceQueryParam(requestParamKey, requestParamValue) {

    let url = getCurrentURL()

    let strRegExPattern = '\\b' + requestParamKey + '\\b=([^&#]*)';

    if (url.match(requestParamKey)) {
        url = url.replace(new RegExp(strRegExPattern), `${requestParamKey}=${requestParamValue}`)
    } else {
        if (!url.includes("?")) {
            url += '?'
        } else {
            url += "&"
        }

        url += `${requestParamKey}=${requestParamValue}`
    }

    return url
}

function getCurrentURL() {
    return window.location.href
}