require ${PN}.inc

PR = "r0"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/core/pim/todo \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/apps \
           file://unbreak-logging.patch \
	   file://gcc-syntax-fix.patch"
