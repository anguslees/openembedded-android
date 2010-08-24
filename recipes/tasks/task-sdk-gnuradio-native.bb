require task-sdk-native.inc

DEPENDS += "libusb1 guile fftw python alsa-lib jack boost cppunit sdcc swig \
            python python-numpy git util-linux-ng gsl python-cheetah git"

RDEPENDS += "libusb1-dev guile-dev fftwf-dev alsa-dev alsa-lib-dev jack-dev \
             cppunit-dev swig python-dev python-numpy-dev python-textutils \
             python-distutils python-re python-stringold python-lang \
             python-threading python-unittest python-shell python-pickle \
             python-pprint python-compiler python-pkgutil python-pydoc \
             python-mmap python-netclient python-difflib python-compile \
             python-cheetah \
             boost-dev gsl-dev sdcc git util-linux-ng util-linux-ng-swaponoff"

RPROVIDES_${PN} = "task-native-gnuradio-sdk"

PR = "${INC_PR}.10"

ALLOW_EMPTY = "1"
PACKAGES = "${PN}"

