require dropbear.inc

PR = "${INC_PR}.0"

SRC_URI += "file://scp-argument-fix.patch"

SRC_URI[md5sum] = "ed2cd18abcebbc7c9dbebe68f605e3bb"
SRC_URI[sha256sum] = "f178c45ae2fa24897c832a81476d22cfd2044d8b06a874d3198cee40e18f9228"
