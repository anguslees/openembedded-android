require gpe-clock.inc

PR = "${INC_PR}.0"

inherit autotools

SRC_URI = "${GPE_SVN} \
           file://svn-build.patch"

S = "${WORKDIR}/${PN}"

DEFAULT_PREFERENCE = "-1"
