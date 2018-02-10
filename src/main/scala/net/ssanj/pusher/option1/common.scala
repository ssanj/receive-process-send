package net.ssanj.pusher.option1

trait EitherOps[L, R] {
  def leftMap[L2](f: L => L2): Either[L2, R]
}

trait Json

trait Decode[A]

trait Encode[A]
