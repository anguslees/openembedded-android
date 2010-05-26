DESCRIPTION = "Camera Assistant aims to be an allround calculator for photographers & Cinematographers. \
It currently has the following features: \
  \
  * Depth of field calculator.  \
  * Inverse Depth of field calculator. (Calculate what aperture you need for a given depth/distance & lens) \
  * Field of view calculator.  \
  * Variants of the above, calculate distance to object or lens for a given field of view.  \
  * Database of TV cameras, Motion picture cameras and Still photography cameras."
SECTION = "opie/applications"


PRIORITY = "optional"
LICENSE = "GPL"
AUTHOR = "zaurus@bredband.net"
HOMEPAGE = "http://cameraassistant.sourceforge.net/"


SRC_URI = "${SOURCEFORGE_MIRROR}/cameraassistant/camera-assistant_0.2.0.tar.gz \
	  file://g++-3.4-compile-fixes.patch \
	  file://ca.desktop \
	  file://ca.png "


S = "${WORKDIR}/CameraAssistant"

APPNAME = "ca"
APPTYPE = "binary"
APPDESKTOP = "${WORKDIR}"

do_install() {
    install -d ${D}${palmtopdir}/pics/${APPNAME}/
    install -m 0644 ${WORKDIR}/ca.png ${D}${palmtopdir}/pics/${APPNAME}/
}


inherit opie


SRC_URI[md5sum] = "538e8d4691062524c4181da487d83250"
SRC_URI[sha256sum] = "9caa3e654af03e55582dd515bfcc9bf5aa93c9d22842af16a87a9a226ec581ed"
