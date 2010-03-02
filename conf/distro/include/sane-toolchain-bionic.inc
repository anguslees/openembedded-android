# bionic

PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}binutils = "binutils-cross"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}libc-for-gcc = "android-ndk-bionic"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}libc-initial = "android-ndk-bionic"
#PREFERRED_PROVIDER_linux-libc-headers = "linux-libc-headers"
PREFERRED_PROVIDER_linux-libc-headers = "android-ndk-bionic"
PREFERRED_PROVIDER_virtual/libc = "android-ndk-bionic"
PREFERRED_PROVIDER_virtual/libintl = "proxy-libintl"
PREFERRED_PROVIDER_virtual/libiconv = "libiconv"
PREFERRED_PROVIDER_libstdc++ = "android-ndk-bionic"
PREFERRED_PROVIDER_libstdc++-dev = "android-ndk-bionic"

USE_NLS ?= "no"
USE_NLS_glib-2.0 = "yes"
USE_NLS_glib-2.0-native = "yes"
USE_NLS_gcc-cross = "no"

#mess with compiler flags to use -Os instead of -O2
#Please see http://free-electrons.com/doc/embedded_linux_optimizations/img47.html for some more info
FULL_OPTIMIZATION = "-Os -fexpensive-optimizations -fomit-frame-pointer -frename-registers -funswitch-loops"
FULL_OPTIMIZATION += "${@['-finline-limit=64', '-finline-limit=300'][bb.data.getVar('ARM_INSTRUCTION_SET', d, 1) == 'thumb']}"

CXXFLAGS += "-fvisibility-inlines-hidden"
