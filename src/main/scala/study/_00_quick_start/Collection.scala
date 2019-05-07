package study._00_quick_start

class Collection {

}

object ListDemo extends App {
  val site1: List[String] = List("AAA", "BBB", "CCC")
  val site2 = "AAA" :: "BBB" :: "CCC" :: Nil

  val nums1: List[Int] = List(1, 2, 3, 4)
  val nums2 = 1 :: (2 :: (3 :: (4 :: Nil)))

  val empty1: List[Nothing] = List()
  val empty2 = Nil

  val dim1: List[List[Int]] =
    List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )
  val dim2 = (1 :: 0 :: 0 :: Nil) :: (0 :: 1 :: 0 :: Nil) :: (0 :: 0 :: 1 :: Nil) :: Nil
  println(site1)
  println(site2)
  println(nums1)
  println(nums2)
  println(empty1)
  println(empty2)
  println(dim1)
  println(dim2)
  println(site1.head) //第一个
  println(site2.tail) //去第一个
  println(site1.isEmpty)
  println(empty1.isEmpty)

  val site = List.fill(3)("AAA")
  //重复AAA 3次
  println(site)
  val num = List.fill(10)(2)
  //重复2, 10次
  println(num)

  // 通过给定的函数创建 5 个元素
  val squares = List.tabulate(6)(n => n * n)
  println( "一维 : " + squares  )

  // 创建二维列表
  val mul = List.tabulate(4, 5)(_*_)
  println( "多维 : " + mul  )

  val site3 = "Runoob" :: ("Google" :: ("Baidu" :: Nil))
  println( "site 反转前 : " + site3 )
  println( "site 反转后 : " + site3.reverse )

  //List添加元素
  val v1 = List(1)
  println(v1)
  val v2 = 2 +: v1
  println(v2)
  val v3 = 3 :: v2
  println(v3)
  val v4 = v3 :+ 4
  println(v4)
}