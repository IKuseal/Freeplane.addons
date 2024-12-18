package org.freeplane.features.custom

fun mapIdFromUriToNode(uri : String) : String {
    println(uri)
    var result = removeFragmentPartFromUriToNode(uri)
    result = result.split("/").last()
    result = result.replace(".mm", "")
    return result
}

fun removeFragmentPartFromUriToNode(uri: String) : String =
    uri.split("#")[0]