require ${PN}.inc

PR = "r0"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/inputmethods/dvorak \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/inputmethods/pickboard \
	   file://fix-rpath.patch "
