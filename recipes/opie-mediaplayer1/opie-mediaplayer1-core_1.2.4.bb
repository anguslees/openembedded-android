require ${PN}.inc

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/core/multimedia/opieplayer \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/pics \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/apps \
           file://double_name.patch"
