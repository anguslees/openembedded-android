require ${PN}.inc

PR = "r0"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/inputmethods/multikey \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/share \
           file://fix-rpath.patch"

#           file://friendly-button-names.patch"
