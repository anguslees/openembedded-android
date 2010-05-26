require pulseaudio.inc

DEPENDS += "gdbm speex"
PR = "${INC_PR}.1"

# work in progress
DEFAULT_PREFERENCE = "-1"

SRC_URI += "\
  file://disable-using-glibc-tls.patch \
  file://buildfix.patch \
#  file://libpulsedsp-references-libpulsecore.patch \
#  file://pa-drop-caps-returns-void.patch \
#  file://libtool2.patch \
  file://2113.diff;striplevel=0 \
  file://2114.diff;striplevel=0 \
#  file://libiconv.patch \
"            

do_compile_prepend() {
    cd ${S}
    mkdir -p ${S}/libltdl
    cp ${STAGING_LIBDIR}/libltdl* ${S}/libltdl
}


# problems w/ pulseaudio 0.9.10 atm:
# 1.) needs libltdl >= 1.5.24 (yes, any older version will NOT work at runtime)
# 2.) doesn't build w/ glibc TLS support (hence patched out)
# 3.) fails with hierarchical pthread stuff w/ gst-pulse (hence patched out)


SRC_URI[md5sum] = "fa121f8e5dd4b98d65096de1b6c84021"
SRC_URI[sha256sum] = "6cc4aff778275f087cfbd65d2040b1587cafc588749499a452eb496329505ab0"
